package com.afeiluo.event_drive;

/**
 * Created by ben on 16/9/23.
 */

/**
 * 守卫任务类
 * <p></p>
 * 当所有具体任务都执行完毕，通知任务管理器关闭
 */
public class GuardTask extends TaskEventEmitter {
    private final int N;

    public GuardTask(final TaskManager manager, int n) {
        super(manager.getExecutor());
        this.N = n;
        on("end", new EventHandler() {
            private int i = 0;

            @Override
            public void handle(EventObject event) {
                i++;
                if (i >= N) {
                    manager.stop();
                }
            }
        });
    }

    @Override
    protected void run() throws Exception {

    }
}
