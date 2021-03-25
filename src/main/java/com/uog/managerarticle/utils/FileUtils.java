package com.uog.managerarticle.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static void saveFileUploaded(Long id, MultipartFile imageFile) throws IOException {
        String file = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String uploadDir = "./file-article/" + id;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = imageFile.getInputStream()) {
            Path filePath = uploadPath.resolve(file);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("could not save uploaded");
        }
    }

    public static void deleteFile(String dirFile) {

        File file = new File(dirFile);
        if(file.exists()) {
            deleteProcess(file);
        }else{
            return;
        }
    }

    public static void deleteProcess(File file) {
        if (file.isDirectory()) {
            // liet ke tat ca thu muc va file
            String[] files = file.list();
            for (String child : files) {
                File childDir = new File(file, child);
                if (childDir.isDirectory()) {
                    // neu childDir la thu muc thi goi lai phuong thuc deleteDir()
                    deleteProcess(childDir);
                } else {
                    // neu childDir la file thi xoa
                    childDir.delete();
                    System.out.println("File bi da bi xoa "
                            + childDir.getAbsolutePath());
                }
            }
            // Check lai va xoa thu muc cha
            if (file.list().length == 0) {
                file.delete();
                System.out.println("File bi da bi xoa " + file.getAbsolutePath());
            }

        } else {
            // neu file la file thi xoa
            file.delete();
            System.out.println("File bi da bi xoa " + file.getAbsolutePath());
        }
    }


}
