package pl.edu.agh.kis.pz1.util;

import org.junit.Assert;
import org.junit.Test;


/**
 * Additional class used for
 * generating hash from given text
 *
 **/
public class TextUtilsTest{

    @Test
    public void shouldConvertStringInto512Hash(){
        Assert.assertEquals(
                "861844d6704e8573fec34d967e20bcfef3d424cf48be04e6dc08f2bd58c729743371015ead891cc3cf1c9d34b49264b510751b1ff9e537937bc46b5d6ff4ecc8",
                TextUtils.sha512Hash("Hello World!"));
    }

    @Test
    public void shouldConvertStringIntoMd5Hash() {
        Assert.assertEquals(
                "ed076287532e86365e841e92bfc50d8c",
                TextUtils.getMd5Hash("Hello World!"));
    }

    @Test
    public void testGenerateHash() {
        Assert.assertNotEquals(
                TextUtils.generateRandomHash(),
                TextUtils.generateRandomHash());
    }
}