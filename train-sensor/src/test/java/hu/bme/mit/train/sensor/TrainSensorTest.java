package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainController mockTrainController;
    TrainUser mockTrainUser;
    TrainSensorImpl trainSensor;

    @Before
    public void before() {
        mockTrainController = mock(TrainController.class);
        mockTrainUser = mock(TrainUser.class);
        trainSensor = new TrainSensorImpl(mockTrainController, mockTrainUser);
    }

    @Test
    public void getSpeedLimitTest(){
        assert trainSensor.getSpeedLimit() == 5;
    }

    @Test
    public void absoluteMarginByUnderMin() {
        trainSensor.overrideSpeedLimit(-1);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void absoluteMarginByOverMax() {
        trainSensor.overrideSpeedLimit(501);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void relativeMargin() {
        trainSensor.overrideSpeedLimit(mockTrainController.getReferenceSpeed() / 2 - 1);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void noAlarm() {
        trainSensor.overrideSpeedLimit(mockTrainController.getReferenceSpeed() / 2 + 1);
        verify(mockTrainUser, times(1)).setAlarmState(false);
    }
}
