package incremenator;

public class Incremenator extends Thread {

    private volatile boolean  mIsIncrement = true;

    public void changeAction() {
        mIsIncrement = !mIsIncrement;
    }

    @Override
    public void run() {
        do
        {
            if(!Thread.interrupted())	//Проверка на необходимость завершения
            {
                if(mIsIncrement)
                    Main.mValue++;	//Инкремент
                else
                    Main.mValue--;	//Декремент

                //Вывод текущего значения переменной
                System.out.print(Main.mValue + " ");
            }
            else
                return;		//Завершение потока

            try{
                Thread.sleep(1000);		//Приостановка потока на 1 сек.
            }catch(InterruptedException e){
                return;
            }
        }
        while(true);
    }
}
