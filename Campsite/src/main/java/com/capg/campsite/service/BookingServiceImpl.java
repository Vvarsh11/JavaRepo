package com.capg.campsite.service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.campsite.entity.Booking;
import com.capg.campsite.exception.ResourceNotFoundException;
import com.capg.campsite.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;
	private static final Logger logger =  LoggerFactory.getLogger(BookingServiceImpl.class);

	@Override
	public List<Booking> getAllBookings() throws ResourceNotFoundException {
		List<Booking> bookings = bookingRepository.findAll();
		if (bookings.isEmpty()) {
			throw new ResourceNotFoundException("Bookings Not Found in Data Base ");
		}
				logger.info("get list of bookings");


		return bookings;
	}

	@Override
	public Booking addBooking(Booking booking) throws Exception {
		if (bookingRepository.findById(booking.getBookingId()).isPresent()) {
			throw new Exception("Booking already Done with id " + booking.getBookingId());
		}
		Booking b = null;
		long days = ChronoUnit.DAYS.between(booking.getBookingDate(), LocalDate.now());
		System.out.println(days);
		if (days >= 30) {
			b = bookingRepository.save(booking);
		} else {
			throw new Exception("Booking Has not Done before one month");
		}
		logger.info("add booking");
		return b;
	}

	@Override
	public Booking getBookingById(long bookingId) throws ResourceNotFoundException {
		logger.info("get booking by Id "+bookingId);

		return bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id : " + bookingId));
	}

	@Override
	public Booking updateBooking(Booking booking) throws ResourceNotFoundException {

		Booking b = bookingRepository.findById(booking.getBookingId()).orElseThrow(
				() -> new ResourceNotFoundException("Booking not found with id : " + booking.getBookingId()));
		bookingRepository.delete(b);
		logger.info("update booking");

		return bookingRepository.save(booking);
	}

	@Override
	public void deleteBooking(long bookingId) throws ResourceNotFoundException {
		Booking b = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id : " + bookingId));
		logger.info("delete booking");
		bookingRepository.delete(b);
	}

	@Override
	public List<Booking> findAvailableBookings(LocalDate arrivalDate, LocalDate departureDate) {

		return bookingRepository.findAll().stream()
				.filter(a -> a.getArrivalDate().equals(arrivalDate) && a.getDepartureDate().equals(departureDate))
				.collect(Collectors.toList());
	}

}
