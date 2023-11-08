package com.julianvalencia.trekdescubre.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.julianvalencia.trekdescubre.model.Rutas
import kotlinx.coroutines.tasks.await

class RutasMeGustaRepository {

    private var db = Firebase.firestore
    suspend fun createRuta(rutas: Rutas): ResourceRemote<String?> {
        return try {
            val document = db.collection("RutasMeGusta").document()
            rutas.id = document.id
            db.collection("RutasMeGusta").document(document.id).set(rutas).await()
            ResourceRemote.Success(data = document.id)
        } catch (e: FirebaseFirestoreException) {
            Log.e("Register", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            Log.e("RegisterNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseException) {
            Log.e("RegisterNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

    suspend fun loadRutas() : ResourceRemote<QuerySnapshot> {
        return try {
            val result = db.collection("RutasMeGusta").get().await()
            ResourceRemote.Success(data = result)
        } catch (e: FirebaseFirestoreException) {
            Log.e("Register", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseNetworkException) {
            Log.e("RegisterNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        } catch (e: FirebaseException) {
            Log.e("RegisterNetwork", e.localizedMessage)
            ResourceRemote.Error(message = e.localizedMessage)
        }
    }

}