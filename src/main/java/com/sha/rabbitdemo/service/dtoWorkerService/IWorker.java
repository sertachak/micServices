package com.sha.rabbitdemo.service.dtoWorkerService;

import com.sha.rabbitdemo.dto.client.ClientInfoDto;
import com.sha.rabbitdemo.dto.client.IDto;

public interface IWorker {
    void doWork(IDto dto);
}
