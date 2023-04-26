package ie.tudublin;

import processing.data.TableRow;

public class task {
    
    private String task;
    private int start;
    private int end;


    @Override
    public String toString() {
        return "task [End=" + end + ", start=" + start + ", task=" + task + "]";
    }


    public task(TableRow row) {
        task = row.getString("Task");
        start = row.getInt("Start");
        end = row.getInt("End");
    }

    public task(String task, int start, int end) {
        this.task = task;
        this.start = start;
        this.end = end;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }

}