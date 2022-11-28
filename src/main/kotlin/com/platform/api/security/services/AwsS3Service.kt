//package com.platform.api.security.services
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.amazonaws.util.IOUtils;
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.web.multipart.MultipartFile
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//
//
//class AwsS3Service(
//        private val amazonS3Client: AmazonS3Client?,
//        @Value("\${cloud.aws.s3.bucket}")
//        private val bucketName: String?
//)
//{
//    fun uploadFile(file: MultipartFile): String? {
//        val fileObj: File = convertMultiPartFileToFile(file)
//        val fileName = System.currentTimeMillis().toString() + "_" + file.originalFilename
//        amazonS3Client.putObject(PutObjectRequest(bucketName, fileName, fileObj))
//        fileObj.delete()
//        return "File uploaded : $fileName"
//    }
//
//    fun deleteFile(fileName: String): String? {
//        amazonS3Client.deleteObject(bucketName, fileName)
//        return "$fileName removed ..."
//    }
//
//    private fun convertMultiPartFileToFile(file: MultipartFile): File {
//        val convertedFile = File(file.originalFilename)
//        try {
//            FileOutputStream(convertedFile).use { fos -> fos.write(file.bytes) }
//        } catch (e: IOException) {
//            log.error("Error converting multipartFile to file", e)
//        }
//        return convertedFile
//    }
//}
