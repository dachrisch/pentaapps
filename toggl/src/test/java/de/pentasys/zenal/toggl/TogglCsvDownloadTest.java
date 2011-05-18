package de.pentasys.zenal.toggl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TogglCsvDownloadTest {

    @Test
    public void testname() throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        HttpHost targetHost = new HttpHost("google.com");

        HttpGet httpget = new HttpGet("/");

        System.out.println("executing request: " + httpget.getRequestLine());
        System.out.println("to target: " + targetHost);

        HttpResponse response = httpclient.execute(targetHost, httpget);
        HttpEntity entity = response.getEntity();

        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (entity != null) {
            System.out.println("Response content length: " + entity.getContentLength());
            System.out.println(EntityUtils.toString(entity));
        }
        assertThat(response.getStatusLine().getStatusCode(), is(200));

        httpclient.getConnectionManager().shutdown();
    }
}
