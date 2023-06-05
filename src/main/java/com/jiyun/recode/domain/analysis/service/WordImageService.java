package com.jiyun.recode.domain.analysis.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.analysis.domain.WordImage;
import com.jiyun.recode.domain.analysis.repository.WordImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class WordImageService {
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private final AmazonS3 amazonS3Client;
	private final WordImageRepository wordImageRepository;

	/**
	 * S3로 파일 업로드
	 */
	public UUID uploadFiles(Account account, MultipartFile multipartFile) {


		String uploadFilePath = account.getNickname() + "/" + getFolderName();


		String originalFileName = multipartFile.getOriginalFilename();
		String uploadFileName = getUuidFileName(originalFileName);
		String uploadFileUrl = "";

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(multipartFile.getSize());
		objectMetadata.setContentType(multipartFile.getContentType());

		try (InputStream inputStream = multipartFile.getInputStream()) {

				String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자
			System.out.println("DJELRKWL어디까지");
				// S3에 폴더 및 파일 업로드
				amazonS3Client.putObject(
						new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));

				// TODO : 외부에 공개하는 파일인 경우 Public Read 권한을 추가, ACL 확인
        /*amazonS3Client.putObject(
            new PutObjectRequest(bucket, s3Key, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));*/

				// S3에 업로드한 폴더 및 파일 URL
				uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();

		} catch (IOException e) {
				e.printStackTrace();
				log.error("Filed upload failed", e);
				throw new RuntimeException("File upload failed", e);
		}

		WordImage wordImage = WordImage.builder()
				.filename(uploadFileName)
				.url(uploadFileUrl)
				.filetype(multipartFile.getContentType())
				.build();
		wordImageRepository.save(wordImage);


		return wordImage.getFileId();
	}

	/**
	 * S3에 업로드된 파일 삭제
	 */
	public String deleteFile(String uploadFilePath, String uuidFileName) {

		String result = "success";

		try {
			String keyName = uploadFilePath + "/" + uuidFileName; // ex) 구분/년/월/일/파일.확장자
			boolean isObjectExist = amazonS3Client.doesObjectExist(bucketName, keyName);
			if (isObjectExist) {
				amazonS3Client.deleteObject(bucketName, keyName);
			} else {
				result = "file not found";
			}
		} catch (Exception e) {
			log.debug("Delete File failed", e);
		}

		return result;
	}

	/**
	 * UUID 파일명 반환
	 */
	public String getUuidFileName(String fileName) {
		String ext = fileName.substring(fileName.indexOf(".") + 1);
		return UUID.randomUUID().toString() + "." + ext;
	}

	/**
	 * 년/월/일 폴더명 반환
	 */
	private String getFolderName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", "/");
	}

	public WordImage findById(UUID id){
		return wordImageRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException()); //TODO: 에외처리
	}
}
