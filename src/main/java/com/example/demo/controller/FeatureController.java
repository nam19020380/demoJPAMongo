package com.example.demo.controller;

import com.example.demo.dto.response.ReportResponse;
import com.example.demo.sercurity.UserDetailsImpl;
import com.example.demo.service.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import com.example.demo.dto.response.PostResponse;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.example.demo.entity.Post;

@RestController
@RequestMapping("/api/v1/feature")
public class FeatureController {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    CommentService commentService;

    @Autowired
    EmoteService emoteService;

    @GetMapping(value = "/newfeed")
    public ResponseEntity<?> getPostForNewFeed(@RequestParam Integer page, @RequestParam Integer perPage) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Pageable pageable = PageRequest.of(page, perPage,
                    Sort.by("date").descending());
            List<PostResponse> postResponses = new ArrayList<>();
            List<Post> postes = postService.findFriendPostByUserId(userDetails.getId(), pageable);
            for (Post post : postes) {
                PostResponse postResponse = new PostResponse();
                postResponse.setId(post.getId());
                postResponse.setText(post.getContent());
                postResponse.setOwnerName(userDetails.getEmail());
                postResponse.setDate(post.getDate());
                postResponse.setCommentCount(commentService.countByPostId(post.getId()));
                postResponse.setEmoteCount(emoteService.countByPostId(post.getId()));
                postResponses.add(postResponse);
            }
            return new ResponseEntity<>(postResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/report")
    public ResponseEntity<?> getPostReport() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String id = userDetails.getId();
            Date sqlDate = new Date(new Date().getTime() - 604800000);
            ReportResponse reportResponse = new ReportResponse();
            reportResponse.setPostNum(postService.countByUserIdAndDateGreaterThanEqual(id, sqlDate));
            reportResponse.setFriendNum(friendshipService.reportCount(id, sqlDate));
            reportResponse.setCommentNum(commentService.countByUserIdAndDateGreaterThanEqual(id, sqlDate));
            reportResponse.setEmoteNum(emoteService.countByUserIdAndDateGreaterThanEqual(id, sqlDate));

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("your activities");
            XSSFRow row;
            Map<String, Object[]> userData = new TreeMap<String, Object[]>();
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();

            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            font.setBold(true);
            font.setFontHeight((short) 13);
            font.setFontHeightInPoints((short) 13);
            cellStyle.setFont(font);

            userData.put("1", new Object[]{"New postes", "New friends", "New comment", "New emotes"});
            userData.put("2", new Object[]{reportResponse.getPostNum().toString(),
                    reportResponse.getFriendNum().toString(),
                    reportResponse.getCommentNum().toString(),
                    reportResponse.getEmoteNum().toString()});
            Set<String> keyId = userData.keySet();
            int rowId = 0;

            for (String key : keyId) {
                row = sheet.createRow(rowId++);
                Object[] objectArr = userData.get(key);
                int cellId = 0;

                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellId++);
                    cell.setCellValue((String) obj);
                    cell.setCellStyle(cellStyle);
                }
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            String fileName = new Date(new Date().getTime()).toString() + ".xlsx";
            fileName = fileName.replaceAll("\\s", "");
            fileName = fileName.replaceAll(":", "-");
            String path = "D:\\Project01\\project01\\src\\main\\java\\reports\\" + fileName;
            FileOutputStream out = new FileOutputStream(path);
            workbook.write(out);
            out.close();
            workbook.close();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(path)));
//            return ResponseEntity.ok()
//                    .contentLength(resource.contentLength())
//                    .header(HttpHeaders.CONTENT_DISPOSITION)
//                    .contentType(new MediaType("application", "force-download"))
//                    .body(resource);
            return new ResponseEntity<>(resource,
                    header, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST
            );
        }
    }

}
