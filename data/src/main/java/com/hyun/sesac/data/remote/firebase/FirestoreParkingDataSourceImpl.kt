package com.hyun.sesac.data.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.sesac.data.datasource.ParkingDataSource
import com.hyun.sesac.data.mapper.toDomainParkingLotList
import com.hyun.sesac.data.mapper.toFirestoreParkingLotDTO
import com.hyun.sesac.data.remote.dto.ParkingLotDTO
import com.hyun.sesac.domain.model.Parking
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreParkingDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ParkingDataSource {

    override fun read(): Flow<List<Parking>> = callbackFlow {
        val listener = firestore.collection("seoul_parking_collection")
            .addSnapshotListener { snapshot, e ->
                if(e != null){
                    close(e)
                    return@addSnapshotListener
                }
                try{
                    val parkingDtoList = snapshot?.toObjects(ParkingLotDTO::class.java) ?: emptyList()
                    trySend(parkingDtoList.toDomainParkingLotList())
                }catch(mappingError: Exception){
                    close(mappingError)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun delete(parkingID: String) {
        firestore.collection("seoul_parking_collection")
            .document(parkingID)
            .delete()
            .await()
    }

    override suspend fun create(parking: Parking) {
        firestore.collection("seoul_parking_collection")
            .document(parking.id)
            .set(parking.toFirestoreParkingLotDTO())
            .await()
    }

    // create와 update 로직이 같음
    override suspend fun update(parking: Parking) {
        firestore.collection("seoul_parking_collection")
            .document(parking.id)
            .set(parking.toFirestoreParkingLotDTO())
            .await()
    }
}