package com.github.martinfrank.eobeditgui;

import com.github.martinfrank.eobedit.image.ImageProvider;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    @Test
    public void test(){
        Assert.assertTrue(true); //rigourious test
        ImageProvider imageProvider = new ImageProvider();
        Assert.assertNotNull(imageProvider.getGuiPageA());
    }

}
