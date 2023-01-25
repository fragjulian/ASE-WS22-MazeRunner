package at.stulpinger.ase;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

public class FileUtil {

  static String encodeFileToBase64(String fileName) throws IOException {
    File file = new File(fileName);
    byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
    return new String(encoded, StandardCharsets.US_ASCII);
  }

}
