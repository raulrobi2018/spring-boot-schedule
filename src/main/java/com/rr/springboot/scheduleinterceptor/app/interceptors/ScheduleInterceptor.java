package com.rr.springboot.scheduleinterceptor.app.interceptors;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("scheduleInterceptor")
public class ScheduleInterceptor implements HandlerInterceptor {

	@Value("${config.schedule.begin}")
	private int begin;
	@Value("${config.schedule.end}")
	private int end;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handelr)
			throws Exception {

		Calendar calendar = Calendar.getInstance();
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

		if (currentHour >= begin && currentHour < end) {
			StringBuilder message = new StringBuilder("Welcome to the customer schedule");
			message.append(", our office hours is from " + begin + " am to " + end + " pm.");
			request.setAttribute("officeHoursMessage", message.toString());
			return true;
		}

		response.sendRedirect(request.getContextPath().concat("/closed"));

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String message = (String) request.getAttribute("officeHoursMessage");

		// Solo con la validación del handler ya bastaría, pero si queremos ser más
		// estrictos
		// podemos validar también el modelAndView
		if (handler instanceof HandlerMethod && modelAndView != null) {
			modelAndView.addObject("officeHours", message);
		}
	}
}
