package com.platform.api.security.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*


@Service
class AwsS3Service(
        @Value("\${cloud.aws.s3.bucket}")
        private val S3Bucket: String? = null,
        @Autowired
        private val amazonS3Client: AmazonS3Client? = null
){
    @Throws(Exception::class)
    fun upload(multipartFileList: Array<MultipartFile>): MutableList<String> {
        val imagePathList: MutableList<String> = ArrayList()
        for (multipartFile in multipartFileList) {
            val originalName = UUID.randomUUID().toString() + "-" + multipartFile.originalFilename // 파일 이름
            val size = multipartFile.size // 파일 크기
            val objectMetaData = ObjectMetadata()
            objectMetaData.contentType = multipartFile.contentType
            objectMetaData.contentLength = size

            // S3에 업로드
            amazonS3Client!!.putObject(
                    PutObjectRequest(S3Bucket, originalName, multipartFile.inputStream, objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            )
            val imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString() // 접근가능한 URL 가져오기
            imagePathList.add(imagePath)
        }
        return imagePathList
    }
}
