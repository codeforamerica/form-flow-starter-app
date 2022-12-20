package org.formflowstartertemplate.app.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import({WebDriverConfiguration.class})
@ActiveProfiles("test")
public abstract class AbstractBasePageTest {
  
  private static final String UPLOADED_JPG_FILE_NAME = "test.jpeg";
  
  @Autowired
  protected RemoteWebDriver driver;

  @Autowired
  protected Path path;

  protected String baseUrl;

  @LocalServerPort
  protected String localServerPort;

  protected Page testPage;

  @BeforeEach
  protected void setUp() throws IOException {
    baseUrl = "http://localhost:%s".formatted(localServerPort);
    driver.navigate().to(baseUrl);
    initTestPage();
  }

  protected void initTestPage() {
    testPage = new Page(driver);
  }

  protected void navigateTo(String pageName) {
    driver.navigate().to(baseUrl + "/pages/" + pageName);
  }

//  protected Map<Document, PDAcroForm> getAllFiles() {
//    return Arrays.stream(Objects.requireNonNull(path.toFile().listFiles()))
//        .filter(file -> file.getName().endsWith(".pdf")).sorted((f1,f2)-> Long.compare(f2.lastModified(), f1.lastModified()))
//        .collect(Collectors.toMap(this::getDocumentType, pdfFile -> {
//          try {
//            return PDDocument.load(pdfFile).getDocumentCatalog().getAcroForm();
//          } catch (IOException e) {
//            throw new IllegalStateException(e);
//          }
//        }, (r1, r2) -> r1));
//  }

  protected List<File> getZipFile() {
    return Arrays.stream(Objects.requireNonNull(path.toFile().listFiles()))
        .filter(file -> file.getName().endsWith(".zip")).toList();
  }

  protected void unzipFiles() {
    List<File> filesList = Arrays.stream(Objects.requireNonNull(path.toFile().listFiles()))
        .filter(file -> file.getName().endsWith(".zip")).collect(Collectors.toCollection(() -> new ArrayList<File>()));
    unzip(filesList);

  }


  protected List<File> unzip(List<File> filesList) {
    List<File> fileList = new ArrayList<File>();
    for(File file: filesList) {
    try {
      FileInputStream inputStream = new FileInputStream(file);
      ZipInputStream zipStream = new ZipInputStream(inputStream);
      ZipEntry zEntry;
      String destination = path.toFile().getPath();
      while ((zEntry = zipStream.getNextEntry()) != null) {
        if(zEntry.getName().contains("_CAF") || zEntry.getName().contains("_CCAP") ) {
          if (!zEntry.isDirectory()) {
            File files = new File(destination, zEntry.getName());
            FileOutputStream fout = new FileOutputStream(files);
            BufferedOutputStream bufout = new BufferedOutputStream(fout);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = zipStream.read(buffer)) != -1) {
              bufout.write(buffer, 0, read);
            }
            zipStream.closeEntry();//This will delete zip folder after extraction
            bufout.close();
            fout.close();
            fileList.add(files);
          }
        }
      }
      zipStream.close();//This will delete zip folder after extraction
    } catch (Exception e) {
      System.out.println("Unzipping failed");
      e.printStackTrace();
    }
    }
    return fileList;
  }


//  private Document getDocumentType(File file) {
//    String fileName = file.getName();
//    if (fileName.contains("_CAF")) {
//      return Document.CAF;
//    } else if (fileName.contains("_CCAP")) {
//      return Document.CCAP;
//    } else {
//      return Document.CAF;
//    }
//  }

//  protected void waitForDocumentUploadToComplete() {
//    await().atMost(15, TimeUnit.SECONDS)
//    .until(() -> driver.findElements(By.linkText("cancel")).isEmpty());
//  }

  @SuppressWarnings("unused")
  public void takeSnapShot(String fileWithPath) {
    TakesScreenshot screenshot = driver;
    Path sourceFile = screenshot.getScreenshotAs(OutputType.FILE).toPath();
    Path destinationFile = new File(fileWithPath).toPath();
    try {
      Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  protected String getAttributeForElementAtIndex(List<WebElement> elementList, int index,
      String attributeName) {
    return elementList.get(index).getAttribute(attributeName);
  }

//  @NotNull
//  protected Callable<Boolean> uploadCompletes() {
//    return () -> !getAttributeForElementAtIndex(driver.findElements(By.className("dz-remove")),
//        0,
//        "innerHTML").isBlank();
//  }

//  protected void fillOutPersonalInfo() {
//    navigateTo("personalInfo");
//    fillOutPersonInfo();
//    testPage.enter("moveToMnPreviousCity", "Chicago");
//  }

//  protected void fillOutPersonInfo() {
//    testPage.enter("firstName", "defaultFirstName");
//    testPage.enter("lastName", "defaultLastName");
//    testPage.enter("otherName", "defaultOtherName");
//    testPage.enter("dateOfBirth", "01/12/1928");
//    testPage.enter("ssn", "123456789");
//    testPage.enter("maritalStatus", "Never married");
//    testPage.enter("sex", "Female");
//    testPage.enter("livedInMnWholeLife", "Yes");
//    testPage.enter("moveToMnDate", "02/18/1776");
//  }

//  protected void fillOutAddress() {
//    testPage.enter("zipCode", "12345");
//    testPage.enter("city", "someCity");
//    testPage.enter("streetAddress", "someStreetAddress");
//    testPage.enter("apartmentNumber", "someApartmentNumber");
//    testPage.enter("isHomeless", "I don't have a permanent address");
//  }

//  protected void fillOutHousemateInfo(String programSelection) {
//    testPage.enter("relationship", "housemate");
//    testPage.enter("programs", programSelection);
//    testPage.enter("firstName", "householdMemberFirstName");
//    testPage.enter("lastName", "householdMemberLastName");
//    testPage.enter("otherName", "houseHoldyMcMemberson");
//    testPage.enter("dateOfBirth", "09/14/1950");
//    testPage.enter("ssn", "987654321");
//    testPage.enter("maritalStatus", "Never married");
//    testPage.enter("sex", "Male");
//    testPage.enter("livedInMnWholeLife", "Yes");
//    testPage.enter("moveToMnDate", "02/18/1950");
//    testPage.enter("moveToMnPreviousState", "Illinois");
//  }

  protected void uploadFile(String filepath, String dzName) {
    testPage.clickElementById("drag-and-drop-box-" + dzName); // is this needed?
    WebElement upload = driver.findElement(By.className("dz-hidden-input"));
    upload.sendKeys(filepath);
    await().until(
        () -> !driver.findElements(By.className("file-details")).get(0).getAttribute("innerHTML")
            .isBlank());
  }

  protected void uploadJpgFile(String dzName) {
    uploadFile(TestUtils.getAbsoluteFilepathString(UPLOADED_JPG_FILE_NAME), dzName);
    assertThat(driver.findElement(By.id("document-upload")).getText())
        .contains(UPLOADED_JPG_FILE_NAME);
  }

//  protected void uploadInvalidJpg(){
//    uploadFile(TestUtils.getAbsoluteFilepathString(UPLOAD_RIFF_WITH_RENAMED_JPG_EXTENSION));
//    assertThat(driver.findElement(By.id("document-upload")).getText())
//        .contains(UPLOAD_RIFF_WITH_RENAMED_JPG_EXTENSION);
//  }

//  protected void uploadButtonDisabledCheck() {
//    testPage.clickElementById("drag-and-drop-box"); // is this needed?
//    WebElement upload = driver.findElement(By.className("dz-hidden-input"));
//    upload.sendKeys(TestUtils.getAbsoluteFilepathString(UPLOADED_JPG_FILE_NAME));
//    assertThat(driver.findElement(By.id("submit-my-documents")).getAttribute("class"))
//    .contains("disabled");
//    await().until(
//        () -> !driver.findElements(By.className("file-details")).get(0).getAttribute("innerHTML")
//            .isBlank());
//    assertThat(driver.findElement(By.id("document-upload")).getText())
//        .contains(UPLOADED_JPG_FILE_NAME);
//  }

//  protected void uploadXfaFormatPdf() {
//    uploadFile(TestUtils.getAbsoluteFilepathString(XFA_PDF_NAME));
//    assertThat(driver.findElement(By.id("document-upload")).getText()).contains(XFA_PDF_NAME);
//  }

//  protected void uploadPasswordProtectedPdf() {
//    uploadFile(TestUtils.getAbsoluteFilepathString(PASSWORD_PROTECTED_PDF));
//    assertThat(driver.findElement(By.id("document-upload")).getText())
//        .contains(PASSWORD_PROTECTED_PDF);
//  }

//  protected void uploadPdfFile() {
//    uploadFile(TestUtils.getAbsoluteFilepathString(UPLOADED_PDF_NAME));
//    assertThat(driver.findElement(By.id("document-upload")).getText()).contains(UPLOADED_PDF_NAME);
//  }
}
