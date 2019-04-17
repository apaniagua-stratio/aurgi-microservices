package com.stratio.microservice.motortownimport.service;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SftpReader {


    public SftpReader(){};

    public List<String> readCsvFileFromSftp(String user,String host, String sftpkey, String remoteFile)
    {
        //String password = ""; // = "Q_7C<p2wGnh4_e{D";
        //host = "10.20.1.112";
        int port=22;

        //String remoteFile="sample.txt";

        try
        {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);


            //change before deploy (no passphrase)
            //String privateKey = "/home/apaniagua/.ssh/id_rsa";
            //jsch.addIdentity(privateKey);


            System.out.println("AURGI SFTP READING KEY" + sftpkey);
            jsch.addIdentity(sftpkey);
            //jsch.addIdentity(sftpkey,"MailSagApm17");

            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("AURGI: Establishing Connection..." + user + "@" + host + " with " + sftpkey);

            session.connect();
            System.out.println("AURGI: Connection established.");

            System.out.println("AURGI: Creating SFTP Channel.");
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            System.out.println("AURGI: SFTP Channel created.");

            InputStream out= null;
            System.out.println("AURGI: SFTP Getting file " + remoteFile);
            out= sftpChannel.get(remoteFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));

            List<String> strings = new ArrayList<String>();

            String line;
            while ((line = br.readLine()) != null)
            {
                //System.out.println(line);
                strings.add(line);
            }
            br.close();
            sftpChannel.disconnect();
            session.disconnect();

            return strings;
        }
        catch(JSchException | SftpException | IOException e)
        {
            log.info("AURGI: " + e);
        }

        return null;

    }


}
