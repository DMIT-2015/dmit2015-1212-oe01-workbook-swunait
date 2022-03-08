package ca.nait.dmit.batch.listener;

import jakarta.batch.api.chunk.listener.AbstractChunkListener;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/***
 * This executes at the beginning and end of chunk processing.
 * To apply this listener to a chunk you must define a listener element in the Job Specification Language (JSL) file
 * INSIDE the step element as follows:
 *
 <step id="step1" >
 <listeners>
 <listener ref="chunkListener" />
 </listeners>
 </step>
 */
@Named
public class EnforcementZoneCentreChunkListener extends AbstractChunkListener {

    private long _startTime;

    @Override
    public void beforeChunk() throws Exception {
        System.out.println("beforeChunk");
        _startTime = System.currentTimeMillis();
    }

    @Override
    public void onError(Exception ex) throws Exception {
        System.out.println("onError: " + ex.getMessage());
    }

    @Override
    public void afterChunk() throws Exception {
        System.out.println("afterChunk");
        long endTime = System.currentTimeMillis();
        long durationMilliSeconds = (endTime - _startTime);
        String message = String.format("Chunk completed in %d milliseconds", durationMilliSeconds);
        System.out.println(message);
    }
}