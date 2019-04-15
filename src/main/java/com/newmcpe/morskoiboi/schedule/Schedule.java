package com.newmcpe.morskoiboi.schedule;

import com.newmcpe.morskoiboi.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

/**
 * Удобные таски
 */
public class Schedule{

    private static Plugin           plugin          = Main.instance;
    private static BukkitScheduler  bukkitScheduler = Bukkit.getScheduler();
    private        ScheduleRunnable schedule;

    public Schedule(Runnable runnable){
        this.schedule = new ScheduleRunnable(runnable);
    }

    /**
     * Создать таск
     * @param runnable код
     * @return таск
     */
    public static Schedule create(Runnable runnable){
        return new Schedule(runnable);
    }

    /**
     * Запустить таск
     * @param runnable таск
     * @return bukkit task
     */
    public static BukkitTask run(Runnable runnable){
        return bukkitScheduler.runTask(plugin, runnable);
    }

    /**
     * Запустить таск асинхронно
     * @param runnable таск
     * @return bukkit task
     */
    public static BukkitTask runAsync(Runnable runnable){
        return bukkitScheduler.runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Выполнить позже
     * @param runnable таск
     * @param delay    через сколько выполнить
     * @param time     тип времени
     * @return bukkit task
     */
    public static BukkitTask later(Runnable runnable, long delay, TimeUnit time){
        return bukkitScheduler.runTaskLater(plugin, runnable, time.toSeconds(delay) * 20);
    }

    /**
     * Запустить таймер
     * @param runnable таск
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять
     * @param time     тип времени
     * @return bukkit task
     */
    public static BukkitTask timer(Runnable runnable, long delay, long period, TimeUnit time){
        return bukkitScheduler.runTaskTimer(plugin, runnable, time.toSeconds(delay) * 20, time.toSeconds(period) * 20);
    }

    /**
     * Выполнить позже
     * @param runnable таск
     * @param delay    через сколько выполнить
     * @return bukkit task
     */
    public static BukkitTask later(Runnable runnable, long delay){
        return bukkitScheduler.runTaskLater(plugin, runnable, delay);
    }

    /**
     * Запустить таймер
     * @param runnable таск
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять тиков
     * @return bukkit task
     */
    public static BukkitTask timer(Runnable runnable, long delay, long period){
        return bukkitScheduler.runTaskTimer(plugin, runnable, delay, period);
    }

    /**
     * Запустить таймер
     * @param runnable таск
     * @param delay    через сколько выполнить
     * @param period   через сколько повторять тиков
     * @return bukkit task
     */
    public static BukkitTask timerAsync(Runnable runnable, long delay, long period){
        return bukkitScheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }

    /**
     * Останавливает таймер.
     * @param runnableID - айди таймера, который будем останавливать.
     */
    public static void stopTimer(int runnableID){
        bukkitScheduler.cancelTask(runnableID);
    }

    /**
     * Установить условие выполенния
     * @return this
     *
    public Schedule predicate(PredicateZero predicate){
        this.schedule.predicate = predicate;
        return this;
    }*

    /**
     * Установить количество срабатываний
     * @param count сколько раз нужно таймеру сработать
     * @return this
     */
    public Schedule count(int count){
        this.schedule.count = count;
        return this;
    }

    /**
     * Запустить таймер
     * @param delay  через сколько первое срабатывание
     * @param period период между запусками
     * @return this
     */
    public Schedule timer(long delay, long period, TimeUnit time){
        this.schedule.runTaskTimer(plugin, this.toTime(delay, time), this.toTime(period, time));
        return this;
    }

    /**
     * Запустить таймер
     * @param delay  через сколько первое срабатывание
     * @param period период между запусками
     * @return this
     */
    public Schedule timer(long delay, long period){
        this.schedule.runTaskTimer(plugin, delay, period);
        return this;
    }

    /**
     * Запустить таск позже
     * @param delay через сколько срабатывание
     * @return this
     */
    public Schedule later(long delay, TimeUnit time){
        this.schedule.runTaskLater(plugin, this.toTime(delay, time));
        return this;
    }

    /**
     * Запустить таск позже
     * @param delay через сколько срабатывание
     * @return this
     */
    public Schedule later(long delay){
        this.schedule.runTaskLater(plugin, delay);
        return this;
    }

    /**
     * Запустить таск
     * @return this
     */
    public Schedule run(){
        this.schedule.runTask(plugin);
        return this;
    }

    private long toTime(long ticks, TimeUnit time){
        return time.toSeconds(ticks) * 20;
    }

    /**
     * Выключить таск
     */
    public void cancel(){
        this.schedule.cancel();
    }

    private class ScheduleRunnable extends BukkitRunnable{

        private Runnable      runnable;
        private PredicateZero predicate;
        private int           count = -1;
        private int           i     = 0;
        private boolean       timer = false;

        ScheduleRunnable(Runnable runnable){
            this.runnable = runnable;
        }

        @Override
        public void run(){
            this.checkCount();
            if(this.hasPredicate()){
                runnable.run();
            }else{
                Schedule.this.cancel();
            }
        }

        private boolean hasPredicate(){
            return predicate == null || predicate.test();
        }

        private void checkCount(){
            if(timer && count != -1 && ++i >= count){
                Schedule.this.cancel();
            }
        }

        @Override
        public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException{
            this.timer = true;
            return super.runTaskTimer(plugin, delay, period);
        }
    }
    public interface PredicateZero{

        boolean test();
    }

}
