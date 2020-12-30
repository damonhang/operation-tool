package com.damon.web;

import static org.junit.Assert.*;

import com.damon.common.GameHttpClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.Setter;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.Contract;
import feign.Headers;
import feign.Logger;
import feign.Logger.JavaLogger;
import feign.Logger.Level;
import feign.Request.Options;
import feign.RequestLine;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.httpclient.ApacheHttpClient;
import feign.hystrix.HystrixDelegatingContract;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import feign.hystrix.SetterFactory.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rx.Completable;
import rx.Observable;
import rx.Single;

public class ItemControllerTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    MockWebServer server = new MockWebServer();

    @Test
    public void defaultMethodReturningHystrixCommand() {

        MockResponse mockResponse = new MockResponse();
        mockResponse.throttleBody(1, 2, TimeUnit.SECONDS);
        mockResponse.setBody("\"foo\"");
        server.enqueue(mockResponse);

        final TestInterface api = target();

        final HystrixCommand<String> command = api.defaultMethodReturningCommand();

        assertNotNull(command);
        System.out.println(System.currentTimeMillis());
        System.out.println(command.execute());
        System.out.println(System.currentTimeMillis());
//        assertEquals(command.execute(),"foo");
    }


    protected TestInterface target() {
        SetterFactory commandKeyIsRequestLine = (target, method) -> {
            String groupKey = target.name();
            return HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andCommandPropertiesDefaults(
                    HystrixCommandProperties.defaultSetter().withExecutionTimeoutEnabled(true));
        };
        Options options = new Options(5000, 5000);
        return HystrixFeign.builder()
            .decoder(new GsonDecoder())
            .options(options)
            .logLevel(Level.FULL)
            .logger(new JavaLogger())
            .contract(new Contract.Default())
            .retryer(Retryer.NEVER_RETRY)
            .setterFactory(commandKeyIsRequestLine)
            .client(new ApacheHttpClient(GameHttpClient.getConnection()))
            .target(TestInterface.class, "http://localhost:" + server.getPort(),
                new FallbackTestInterface());
    }

    protected TestInterface targetWithoutFallback() {
        return HystrixFeign.builder()
            .decoder(new GsonDecoder())
            .target(TestInterface.class, "http://localhost:" + server.getPort());
    }

    interface OtherTestInterface {

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        HystrixCommand<List<String>> listCommand();
    }

    interface TestInterface {

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        HystrixCommand<List<String>> listCommand();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
        HystrixCommand<String> command();

        default HystrixCommand<String> defaultMethodReturningCommand() {
            return command();
        }

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        HystrixCommand<Integer> intCommand();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        Observable<List<String>> listObservable();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        Observable<String> observable();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        Single<Integer> intSingle();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        Single<List<String>> listSingle();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        Single<String> single();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        Observable<Integer> intObservable();


        @RequestLine("GET /")
        @Headers("Accept: application/json")
        String get();

        @RequestLine("GET /")
        @Headers("Accept: application/json")
        List<String> getList();

        @RequestLine("GET /")
        Completable completable();

        @RequestLine("GET /")
        CompletableFuture<String> completableFuture();
    }

    class FallbackTestInterface implements TestInterface {

        @Override
        public HystrixCommand<String> command() {
            return new HystrixCommand<String>(HystrixCommandGroupKey.Factory.asKey("Test")) {
                @Override
                protected String run() throws Exception {
                    return "fallback";
                }
            };
        }

        @Override
        public HystrixCommand<List<String>> listCommand() {
            return new HystrixCommand<List<String>>(HystrixCommandGroupKey.Factory.asKey("Test")) {
                @Override
                protected List<String> run() throws Exception {
                    final List<String> fallbackResult = new ArrayList<String>();
                    fallbackResult.add("fallback");
                    return fallbackResult;
                }
            };
        }

        @Override
        public HystrixCommand<Integer> intCommand() {
            return new HystrixCommand<Integer>(HystrixCommandGroupKey.Factory.asKey("Test")) {
                @Override
                protected Integer run() throws Exception {
                    return 0;
                }
            };
        }

        @Override
        public Observable<List<String>> listObservable() {
            final List<String> fallbackResult = new ArrayList<String>();
            fallbackResult.add("fallback");
            return Observable.just(fallbackResult);
        }

        @Override
        public Observable<String> observable() {
            return Observable.just("fallback");
        }

        @Override
        public Single<Integer> intSingle() {
            return Single.just(0);
        }

        @Override
        public Single<List<String>> listSingle() {
            final List<String> fallbackResult = new ArrayList<String>();
            fallbackResult.add("fallback");
            return Single.just(fallbackResult);
        }

        @Override
        public Single<String> single() {
            return Single.just("fallback");
        }

        @Override
        public Observable<Integer> intObservable() {
            return Observable.just(0);
        }

        @Override
        public String get() {
            return "fallback";
        }

        @Override
        public List<String> getList() {
            final List<String> fallbackResult = new ArrayList<String>();
            fallbackResult.add("fallback");
            return fallbackResult;
        }

        @Override
        public Completable completable() {
            return Completable.complete();
        }

        @Override
        public CompletableFuture<String> completableFuture() {
            return CompletableFuture.completedFuture("fallback");
        }
    }
}