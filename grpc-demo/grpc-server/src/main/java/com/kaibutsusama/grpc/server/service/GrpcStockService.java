package com.kaibutsusama.grpc.server.service;

import com.kaibutsusama.grpc.lib.StockServiceGrpc;
import com.kaibutsusama.grpc.lib.StockServiceReply;
import com.kaibutsusama.grpc.lib.StockServiceRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Random;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 16:05
 */
@GrpcService
public class GrpcStockService extends StockServiceGrpc.StockServiceImplBase {

    @Override
    public void getStockPrice(StockServiceRequest request, StreamObserver<StockServiceReply> responseObserver) {
        String msg = "股票名称:" + request.getName() + ", 股票价格:" + (new Random().nextInt(100-20)+20);
        StockServiceReply reply =StockServiceReply.newBuilder().setMessage(msg).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
