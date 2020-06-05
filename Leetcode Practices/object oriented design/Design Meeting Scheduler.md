https://leetcode.com/discuss/interview-question/object-oriented-design/490962/Design-Meeting-Scheduler  
  
Q:  
Design Meeting Scheduler. Here there are n given meeting rooms. Book a meeting in any meeting room at given interval(starting time, end time). Also send notifications to all person who are invited for meeting.  
You should use calender for tracking date and time. And also history of all the meetings which are booked and meeting room.  
write an API for client who will give date and time and API should return meeting room with booked scheduled time. client should also query for history of last 20 booked meetings.  
Is meeting room available? etc  
  
A:  
other's solution:  
```java
class Scheduler {
	private static final int MAX_HISTORICAL_MEETING_STORAGE = 20;

	List<MeetingRoom> meetingRooms;
	Meeting[] history;
	int historySize;

	public Scheduler(List<MeetingRoom> rooms) {
		this.meetingRooms = rooms;
		this.history = new Meeting[MAX_HISTORICAL_MEETING_STORAGE];
		this.historySize = 0;
	}

	public Meeting book(Date start, Date end) throws NoMeetingRoomsAvailableException {
		try {
			for (MeetingRoom room : meetingRooms) {
				if (room.isAvailable(start, end)) {
					Meeting meeting = room.scheduleMeeting(start, end);
					saveToHistory(meeting);
					return meeting;
				}
			}
		} catch (Exception e) {
			System.out.println(e.message);
		}
		
		throw new NoMeetingRoomsAvailableException();
	}

	private void saveToHistory(Meeting meeting) {
		if (historySize == MAX_HISTORICAL_MEETING_STORAGE) {
			history[0] = null;
			for (int i = 1; i < MAX_HISTORICAL_MEETING_STORAGE; i++) {
				history[i - 1] = history[i]; 
			}
		}

		history[historySize++] = meeting;
	}

	public void invite(Meeting meeting, List<Attendee> attendees) {
		meeting.invite(attendees);
	}

}

// An assumption here is that each meeting room has infinite capacity.
class MeetingRoom {
	Calendar calendar;

	public boolean isAvailable(Date start, Date end) {
		return calendar.checkAvailability(start, end);
	}

	public Meeting scheduleMeeting(Date start, Date end) {
		return calendar.scheduleNewMeeting(start, end);
	}
}

class Calendar { // Java 内部类？
	MeetingRoom room;
	List<Meeting> meetings;

	public Calendar() {
		this.meetings = new ArrayList<>();
	}

	public boolean checkAvailability(Date start, Date end) {
		for (Meeting meeting : meetings) {
			if (meeting.end > start || meeting.start < end)
				return false;
		}
		return true;
	}

	public Meeting scheduleNewMeeting(Date start, Date end) {
		Meeting meeting = new Meeting(room, start, end);
		meetings.add(meeting);
		return meeting;
	}
}

class Meeting {
	int id;
	MeetingRoom location;
	List<Attendee> attendees;
	Date start;
	Date end;
	EmailService emailService;
	
	public Meeting(MeetingRoom location, Date start, Date end) {
		this.id = generateId();
		this.location = location;
		this.start = start;
		this.end = end;
		this.emailService = new EmailService();
	}

	/**
	 * Use AWS SES for transactional emails.
	 */
	public void invite(List<Attendee> attendees) {
		// this is a bit of Groovy, just for fun.
		emailService.sendBulkEmail(
			EMAIL_NOTIFICATION_TEMPLATE_NAME, 
			attendees.collect { return it.email },
			[ meeting: this ]
		);
	}
}

class Attendee {
	String name;
	String email;
}

class EmailService {
	SESClient client;

	public void sendBulkEmail(String template, List<String> emails, Map<String, Object> model) {
		for (String email : emails) {
			sendEmail(String template, email, model);
		}
	}

	private boolean sendEmail(String template, String email, Map<String, Object> model) {
		return client.sendTemplateEmail(template, email, model);
	}
}
```