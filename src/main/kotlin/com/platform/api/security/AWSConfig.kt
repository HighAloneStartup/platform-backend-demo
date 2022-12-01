package com.platform.api.security

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AWSConfig (
        @Value("\${cloud.aws.credentials.accessKey}")
        private val iamAccessKey : String,
        @Value("\${cloud.aws.credentials.secretKey}")
        private val iamSecretKey : String,
        @Value("\${cloud.aws.region.static}")
        private val region: String
){
    @Bean
    open fun amazonS3Client(): AmazonS3Client {
        val basicAWSCredentials = BasicAWSCredentials(iamAccessKey, iamSecretKey)
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
                .build() as AmazonS3Client
    }
}