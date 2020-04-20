package com.pakisoft.wordfinder.infrastructure.dictionary.polish.sjp

import mockit.Mock
import mockit.MockUp
import mockit.integration.junit4.JMockit
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.net.ssl.SSLSocketFactory

@RunWith(JMockit)
class HtmlDocumentFetcherUT extends Specification {

    def retriever = Spy(HtmlDocumentFetcher)

    static htmlDoc = new Document('url')

    def "should get HTML document"() {
        given:
        new MockUp<Jsoup>() {

            @Mock
            static Connection connect(String docurl) {
                return connection()
            }

        }

        expect:
        htmlDoc == retriever.getDocument('url')
    }

    static connection() {
        new Connection() {
            @Override
            Connection url(URL url) {
                return null
            }

            @Override
            Connection url(String url) {
                return null
            }

            @Override
            Connection proxy(Proxy proxy) {
                return null
            }

            @Override
            Connection proxy(String host, int port) {
                return null
            }

            @Override
            Connection userAgent(String userAgent) {
                return null
            }

            @Override
            Connection timeout(int millis) {
                return null
            }

            @Override
            Connection maxBodySize(int bytes) {
                return null
            }

            @Override
            Connection referrer(String referrer) {
                return null
            }

            @Override
            Connection followRedirects(boolean followRedirects) {
                return null
            }

            @Override
            Connection method(Connection.Method method) {
                return null
            }

            @Override
            Connection ignoreHttpErrors(boolean ignoreHttpErrors) {
                return null
            }

            @Override
            Connection ignoreContentType(boolean ignoreContentType) {
                return null
            }

            @Override
            Connection sslSocketFactory(SSLSocketFactory sslSocketFactory) {
                return null
            }

            @Override
            Connection data(String key, String value) {
                return null
            }

            @Override
            Connection data(String key, String filename, InputStream inputStream) {
                return null
            }

            @Override
            Connection data(String key, String filename, InputStream inputStream, String contentType) {
                return null
            }

            @Override
            Connection data(Collection<Connection.KeyVal> data) {
                return null
            }

            @Override
            Connection data(Map<String, String> data) {
                return null
            }

            @Override
            Connection data(String... keyvals) {
                return null
            }

            @Override
            Connection.KeyVal data(String key) {
                return null
            }

            @Override
            Connection requestBody(String body) {
                return null
            }

            @Override
            Connection header(String name, String value) {
                return null
            }

            @Override
            Connection headers(Map<String, String> headers) {
                return null
            }

            @Override
            Connection cookie(String name, String value) {
                return null
            }

            @Override
            Connection cookies(Map<String, String> cookies) {
                return null
            }

            @Override
            Connection parser(Parser parser) {
                return null
            }

            @Override
            Connection postDataCharset(String charset) {
                return null
            }

            @Override
            Document get() throws IOException {
                return htmlDoc
            }

            @Override
            Document post() throws IOException {
                return null
            }

            @Override
            Connection.Response execute() throws IOException {
                return null
            }

            @Override
            Connection.Request request() {
                return null
            }

            @Override
            Connection request(Connection.Request request) {
                return null
            }

            @Override
            Connection.Response response() {
                return null
            }

            @Override
            Connection response(Connection.Response response) {
                return null
            }
        }
    }

}
