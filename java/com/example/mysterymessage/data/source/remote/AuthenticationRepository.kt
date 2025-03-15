package com.example.mysterymessage.data.source.remote

import android.util.Log
import com.example.mysterymessage.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("UNCHECKED_CAST")
class AuthenticationRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Hàm đăng ký tài khoản
    fun registerUser(
        user: User,
        onResult: (Boolean, String?) -> Unit
    ) {
        val usersRef = db.collection("users")

        usersRef.whereEqualTo("userName", user.userName)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (!task.result.isEmpty) {
                        onResult(false, "Tên người dùng đã được sử dụng, hãy chọn tên khác.")
                        return@addOnCompleteListener
                    }
                    // Nếu username chưa tồn tại, tiến hành đăng ký
                    auth.createUserWithEmailAndPassword(user.email, user.password)
                        .addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                val uid = auth.currentUser?.uid ?: return@addOnCompleteListener onResult(false, "Không thể lấy UID")

                                val userMap = hashMapOf(
                                    "uid" to uid,
                                    "email" to user.email,
                                    "userName" to user.userName,
                                    "avatar" to user.avatar,
                                    "token" to user.token,
                                    "display_name" to user.displayName,
                                    "friends" to user.friends,
                                    "friendRequests" to user.friendRequests,
                                    "createdAt" to FieldValue.serverTimestamp()
                                )

                                db.collection("users").document(uid)
                                    .set(userMap)
                                    .addOnCompleteListener { firestoreTask ->
                                        if (firestoreTask.isSuccessful) {
                                            onResult(true, null)
                                        } else {
                                            onResult(false, firestoreTask.exception?.message)
                                        }
                                    }
                            } else {
                                Log.e("FirebaseAuth", "Lỗi đăng nhập: ${task.exception?.message}")

                                onResult(false, authTask.exception?.message)
                            }
                        }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }



    // Hàm đăng nhập và lấy thông tin người dùng từ Firestore
    fun loginUser(
        email: String,
        password: String,
        token: String,
        onResult: (Boolean, String?, User?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid == null) {
                        onResult(false, "Không lấy được UID", null)
                        return@addOnCompleteListener
                    }

                    val userRef = db.collection("users").document(uid)

                    userRef.get().addOnCompleteListener { firestoreTask ->
                        if (firestoreTask.isSuccessful) {
                            val document = firestoreTask.result

                            // 🔴 Kiểm tra document có tồn tại không
                            if (document == null || !document.exists()) {
                                onResult(false, "Không tìm thấy thông tin người dùng", null)
                                return@addOnCompleteListener
                            }

                            // 🔄 Cập nhật token trước khi trả về
                            userRef.update("token", token)
                                .addOnSuccessListener {
                                    val user = User(
                                        uid = document.getString("uid") ?: "",
                                        email = document.getString("email") ?: "",
                                        userName = document.getString("userName") ?: "",
                                        avatar = document.getString("avatar") ?: "",
                                        displayName = document.getString("display_name")?:"",
                                        token = token,
                                        friends = document.get("friends") as? MutableList<String> ?: mutableListOf(),
                                        friendRequests = document.get("friendRequests") as? MutableList<String> ?: mutableListOf()
                                    )
                                    onResult(true, null, user)
                                }
                                .addOnFailureListener { e ->
                                    onResult(false, "Cập nhật token thất bại: ${e.message}", null)
                                }
                        } else {
                            val errorMsg = firestoreTask.exception?.message ?: "Lỗi không xác định"
                            onResult(false, "Lỗi lấy dữ liệu Firestore: $errorMsg", null)
                        }
                    }
                } else {
                    onResult(false, authTask.exception?.message, null)
                }
            }
    }


}