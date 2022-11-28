//package com.platform.api.controllers
//
//import org.springframework.core.io.ByteArrayResource
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.multipart.MultipartFile
//
//
//class PhotoController(
//        private val awsS3Service: AwsS3Service
//)
//{
//    @PostMapping("photo/upload")
//    fun uploadFile(@RequestParam(value = "file") file: MultipartFile?): ResponseEntity<*> {
//        return ResponseEntity.ok(awsS3Service.uploadFile(file))
//    }
//
//    @GetMapping("/download/{fileName}")
//    fun downloadFile(@PathVariable fileName: String): ResponseEntity<ByteArrayResource>? {
//        val data: ByteArray = awsS3Service.downloadFile(fileName)
//        val resource = ByteArrayResource(data)
//        return ResponseEntity
//                .ok()
//                .contentLength(data.size.toLong())
//                .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attachment; filename=\"$fileName\"")
//                .body(resource)
//    }
//
//    @DeleteMapping("photo/delete/{fileName}")
//    fun deleteFile(@PathVariable fileName: String?): ResponseEntity<*> {
//        return ResponseEntity.ok(awsS3Service.deleteFile(fileName))
//    }
//}