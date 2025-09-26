package com.medicalhealth.healthapplication.dependeceyInjection

import com.google.firebase.firestore.FirebaseFirestore
import com.medicalhealth.healthapplication.model.repository.DoctorDetailsRepository
import com.medicalhealth.healthapplication.model.repository.DoctorDetailsRepositoryImpl
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepository
import com.medicalhealth.healthapplication.model.repository.doctorBooking.BookingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideBookingDoctorRepository(firestore: FirebaseFirestore): BookingRepository{
        return BookingRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideDoctorRepository(firestore: FirebaseFirestore): DoctorDetailsRepository{
        return DoctorDetailsRepositoryImpl(firestore)
    }
}