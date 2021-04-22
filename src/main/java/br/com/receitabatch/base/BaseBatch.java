package br.com.receitabatch.base;

import org.springframework.beans.factory.annotation.Value;

public abstract class BaseBatch {

    public static final String SISTEMA_BATCH = "Sistema REC receita";

    @Value("${rec-dir.rec-out}")
    private String pathFtpRecOut;

    @Value("${rec-dir.rec-in}")
    private String pathFtpRecIn;

    @Value("${rec-dir.rec-erro}")
    private String pathFtpRecErro;

    @Value("${rec-dir.rec-process}")
    private String pathFtpRecProcess;

    public String getPathFtpRecOut() { return pathFtpRecOut.concat("/"); }

    public String getPathFtpRecIn() { return pathFtpRecIn.concat("/"); }

    public String getPathFtpRecErro() { return pathFtpRecErro.concat("/"); }

    public String getPathFtpRecProcess() { return pathFtpRecProcess.concat("/"); }

}
