
package br.com.jpbx.util;

import br.com.jpbx.model.Queue;
import java.util.Date;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
public class DacSinteticFilter{
    
    private Date start;
    private Date end;
    private Queue queue;

    public DacSinteticFilter(Date start, Date end, Queue queue) {
        this.start = start;
        this.end = end;
        this.queue = queue;
    }

    public DacSinteticFilter() {
    }
    
    

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
    
    
}
