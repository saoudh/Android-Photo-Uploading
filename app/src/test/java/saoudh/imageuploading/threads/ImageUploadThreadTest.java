package saoudh.imageuploading.threads;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import saoudh.imageuploading.HelperClass;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(HelperClass.class)
public class ImageUploadThreadTest {


    @Test
    public void uploadImageWithCorrectImgPATH() {
        ImageUploadThread test = mock(ImageUploadThread.class);
        mockStatic(HelperClass.class);

        String correctImgPath = "/home/admin/Documents/img1.jpg";
        when(HelperClass.encodeBase64String(correctImgPath)).thenReturn("");

        test.start();
        try {
            test.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(test.getStatus(), is(true));


    }

    @Test
    public void uploadImageWithFalseImgPATH() {

        ImageUploadThread test = mock(ImageUploadThread.class);
        mockStatic(HelperClass.class);

        String falseImgPath = "/home/admin/Documents/img2.jpg";

        when(HelperClass.encodeBase64String(falseImgPath)).thenReturn("");

        test.start();
        try {
            test.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(test.getStatus(), is(false));


    }
}
