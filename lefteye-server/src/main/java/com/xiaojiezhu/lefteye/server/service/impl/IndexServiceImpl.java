package com.xiaojiezhu.lefteye.server.service.impl;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.model.Process;
import com.xiaojiezhu.lefteye.core.util.ProcessUtils;
import com.xiaojiezhu.lefteye.server.service.IndexService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 16:15
 */
@Service
public class IndexServiceImpl implements IndexService {


    @Override
    public List<Process> listProcess() {
        Map<Integer, String> integerStringMap = ProcessUtils.listProcessByJps(false);
        if(integerStringMap == null){
            return new ArrayList<>();
        }

        List<Process> processes = new ArrayList<>(integerStringMap.size());
        for (Map.Entry<Integer, String> entry : integerStringMap.entrySet()) {
            Process process = new Process();
            process.setPid(entry.getKey());
            process.setHost("localhost");
            process.setName(entry.getValue());
            process.setPath("java");

            processes.add(process);
        }


        return processes;
    }


}
