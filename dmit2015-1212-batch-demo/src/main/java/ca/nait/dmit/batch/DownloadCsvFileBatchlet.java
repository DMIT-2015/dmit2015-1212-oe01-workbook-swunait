package ca.nait.dmit.batch;

import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.api.BatchProperty;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.context.JobContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Named("downloadManager")
public class DownloadCsvFileBatchlet extends AbstractBatchlet {

    @Inject
    private JobContext _jobContext;

    @Inject
    @BatchProperty(name = "file_download_path")
    private String _fileDownloadPath;

    @Inject
    @BatchProperty(name = "csv_file_uri")
    private String _csvFileUri;

    @Override
    public String process() throws Exception {
        String batchStatus = BatchStatus.COMPLETED.toString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(_csvFileUri))
                .build();
        Path downloadPath = Path.of(_fileDownloadPath);	// On a Windows double back-slash such as C:\\DMIT2015\\Downloads
        HttpResponse<Path> response = client.send(request,
                HttpResponse.BodyHandlers.ofFileDownload(downloadPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
        if (response.statusCode() != Response.Status.OK.getStatusCode()) {
            batchStatus = BatchStatus.FAILED.toString();
        }

        return batchStatus;
    }
}
