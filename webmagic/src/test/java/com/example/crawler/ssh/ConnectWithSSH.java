/**
 * Copyright (C), 2017-2017, 帮5采
 * FileName: Main
 * Author:   tianyi
 * Date:     2017/11/8 16:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.crawler.ssh;

import net.neoremind.sshxcute.core.ConnBean;
import net.neoremind.sshxcute.core.Result;
import net.neoremind.sshxcute.core.SSHExec;
import net.neoremind.sshxcute.task.CustomTask;
import net.neoremind.sshxcute.task.impl.ExecCommand;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author tianyi
 * @create 2017/11/8
 * @since 1.0.0
 */
public class ConnectWithSSH {

    public static void main(String[] args) {
        ConnBean cb = new ConnBean("172.16.13.26", "root", "root");
        CustomTask ct1 = new ExecCommand("ls -l /opt/");
        SSHExec ssh = null;
        try {
            ssh = SSHExec.getInstance(cb);
            ssh.connect();
            Result res = ssh.exec(ct1);
            if (res.isSuccess) {
                System.out.println("Return code: " + res.rc);
                System.out.println("sysout: " + res.sysout);
            } else {
                System.out.println("Return code: " + res.rc);
                System.out.println("error message: " + res.error_msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ssh.disconnect();
        }
    }
}