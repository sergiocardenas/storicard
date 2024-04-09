package com.sc.card.data.datasource

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sc.card.data.entity.UserEntity
import com.sc.card.presenter.extension.emailToKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val LOGIN_TIMEOUT = 7000L
class RemoteDataSourceImp @Inject constructor(
    database: FirebaseDatabase
): RemoteDataSource {
    private val userModule = database.getReference("users")
    override suspend fun registerUser(user: UserEntity) : Flow<Boolean> = flow{
        userModule.child(user.email.emailToKey()).child("email").setValue(user.email)
        userModule.child(user.email.emailToKey()).child("name").setValue(user.name)
        userModule.child(user.email.emailToKey()).child("lastName").setValue(user.lastName)
        userModule.child(user.email.emailToKey()).child("password").setValue(user.password)
        userModule.child(user.email.emailToKey()).child("photo").setValue(user.photo)
        emit(true)

    }

    override suspend fun login(user: UserEntity, callback: (UserEntity) -> Unit) {
        withContext(Dispatchers.IO){
            var snapshotSuccess = false
            userModule.addListenerForSingleValueEvent(
                object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshotSuccess = true
                        if(snapshot.hasChild(user.email.emailToKey())){
                            val password = snapshot.child(user.email.emailToKey())
                                .child("password").getValue(String::class.java)
                            if(password.equals(user.password)){
                                callback(
                                    UserEntity(
                                        email = snapshot.child(user.email.emailToKey())
                                            .child("email").getValue(String::class.java).toString(),
                                        password = snapshot.child(user.email.emailToKey())
                                            .child("password").getValue(String::class.java).toString(),
                                        name = snapshot.child(user.email.emailToKey())
                                            .child("name").getValue(String::class.java).toString(),
                                        lastName = snapshot.child(user.email.emailToKey())
                                            .child("lastName").getValue(String::class.java).toString(),
                                        photo = snapshot.child(user.email.emailToKey())
                                            .child("photo").getValue(String::class.java).toString(),
                                    )
                                )
                            }else{
                                callback(
                                    UserEntity(
                                        email = "error"
                                    )
                                )
                            }
                        }else{
                            callback(UserEntity())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(UserEntity())
                    }
                }
            )
            delay(LOGIN_TIMEOUT)
            if(!snapshotSuccess){
                callback(UserEntity())
            }
        }
    }

}