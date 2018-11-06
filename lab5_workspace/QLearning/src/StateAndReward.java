public class StateAndReward {

	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {
		/* 10 different states based on the angle */
		int state_int = discretize(angle, 10, -Math.PI, Math.PI);
		return Integer.toString(state_int);
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {
		/* Reward based on the angle */
		return 10 - (2*Math.abs(angle));
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {
		/* State with vx and vy included, 10 states for angle, 3 for vx and 4 for vy. 120 total states */
		/* Discretize each value to simplify conversion value -> state */
		int state_angle = discretize(angle, 10, -Math.PI, Math.PI);
		int state_vx = discretize(vx, 3, -3, 3);
		int state_vy = discretize(vy, 4, -4, 4);
		/* Create states in the form "angle:vx:vy" */
		String state = Integer.toString(state_angle) + ":" + Integer.toString(state_vx) + ":" + Integer.toString(state_vy);
		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {
		/* Reward based on the angle, vx and vy */
		/* Increase reward the close the rocket is to facing up and hovering. */
		double reward = 0;

		if (angle < (Math.PI / 2) && angle > -(Math.PI / 2))
			reward += 10;
		else if (angle < (Math.PI / 4) && angle > -(Math.PI / 4))
			reward += 20;
		else if (angle < (Math.PI / 6) && angle > -(Math.PI / 6))
			reward += 25;
		
		if (vy < 2 && vy > -2)
			reward += 2;
		if (vy < 1 && vy > -1)
			reward += 4;
		if (vy < 0.3 && vy > -0.3)
			reward += 8;
		if (vy < 0.1 && vy > -0.1)
			reward += 16;
		if (vx < 2 && vx > -2)
			reward += 2;
		if (vx < 1 && vy > -1)
			reward += 4;
		if (vx < 0.3 && vx > -0.3)
			reward += 8;
		if (vx < 0.1 && vx > -0.1)
			reward += 16;
		
		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
