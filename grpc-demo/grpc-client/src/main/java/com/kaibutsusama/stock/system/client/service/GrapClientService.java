package com.kaibutsusama.stock.system.client.service;

import com.kaibutsusama.grpc.lib.StockServiceGrpc;
import com.kaibutsusama.grpc.lib.StockServiceReply;
import com.kaibutsusama.grpc.lib.StockServiceRequest;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 16:09
 */
@Service
public class GrapClientService {

    @GrpcClient("grpc-server")
    private StockServiceGrpc.StockServiceBlockingStub stockServiceBlockingStub;

    public String getStockPrice(final String name){
        try {
            final StockServiceReply response = stockServiceBlockingStub.getStockPrice(StockServiceRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "error";
        }
    }
}
