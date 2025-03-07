
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Test1 {





                private static final String TEST_FILE_PATH = "test.txt";
                private static final String TEST_CONTENT = "这是一段测试内容";

                @BeforeEach
                public void setUp() throws IOException {
                        // 创建测试文件并写入初始内容
                        Files.write(Paths.get(TEST_FILE_PATH), TEST_CONTENT.getBytes(StandardCharsets.UTF_8));
                }

                @AfterEach
                public void tearDown() throws IOException {
                        // 删除测试文件
                        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
                }

                @org.testng.annotations.Test
                public void testReadFileAsync() throws ExecutionException, InterruptedException {
                        CompletableFuture<String> futureContent = FileHandler.readFileAsync(TEST_FILE_PATH);
                        String content = futureContent.get();
                        Assertions.assertEquals(TEST_CONTENT + "\n", content, "文件内容读取不正确");
                }

                @org.testng.annotations.Test
                public void testWriteFile() throws IOException {
                        String newContent = "这是新的测试内容";
                        FileHandler.writeFile(TEST_FILE_PATH, newContent);
                        String content = new String(Files.readAllBytes(Paths.get(TEST_FILE_PATH)), StandardCharsets.UTF_8);
                        Assertions.assertEquals(newContent, content, "文件内容写入不正确");
                }

                @org.testng.annotations.Test
                public void testReadFileAsyncWithNonexistentFile() {
                        CompletableFuture<String> futureContent = FileHandler.readFileAsync("nonexistent.txt");
                        Assertions.assertThrows(ExecutionException.class, futureContent::get, "应该抛出文件读取失败的异常");
                }

                @org.testng.annotations.Test
                public void testWriteFileWithInvalidPath() {
                        Assertions.assertThrows(IOException.class, () -> FileHandler.writeFile("/invalid/path/test.txt", TEST_CONTENT), "应该抛出文件写入失败的异常");
                }
        }












