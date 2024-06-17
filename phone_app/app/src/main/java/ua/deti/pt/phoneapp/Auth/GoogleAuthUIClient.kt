package ua.deti.pt.phoneapp.Auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import co.yml.charts.common.extensions.isNotNull
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.database.daos.UserDao
import ua.deti.pt.phoneapp.database.entities.User

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val userDao: UserDao
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    // omg Zé, isto do intent é bué esquisito, mas pesquisa e percebes, passas o intent do signIn para aqui
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user

            if (user?.email != null && user.displayName != null) {
                Log.i("GoogleAuthUiClient", "Saving user")
                saveUser(user.email!!, user.displayName!!)
            }

            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    private suspend fun saveUser(email: String, userName: String) {
        // Switch to a IO Dispatcher for a background work cause room doesn't allow operations
        // involving the database in the UI thread cause it might block it
        withContext(Dispatchers.IO) {
            val possibleUserByEmail = userDao.getUserByEmail(email)
            val possibleUserByName = userDao.getUserByName(userName)

            if (!possibleUserByName.isNotNull() && !possibleUserByEmail.isNotNull()) {
                val newUser = User(name = userName, email = email)
                userDao.upsertUser(newUser)
                Log.i("StoreUser", "User saved with success")
            } else {
                Log.i("StoreUser", "User already exists")
            }
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}