package com.sha.rabbitdemo.service.dtoWorkerService;

import com.sha.rabbitdemo.dto.client.ClientInfoDto;
import com.sha.rabbitdemo.dto.client.IDto;

public class ClientDtoWorker implements IWorker {
    @Override
    public void doWork(IDto dto) {
        System.out.println("ClientDtoWorker.doWork");

    }
}
