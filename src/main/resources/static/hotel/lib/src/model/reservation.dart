class Reservation {
  final int roomId;
  final int guestId;
  final String roomName;
  final String roomNumber;
  final String firstName;
  final String lastName;
  final DateTime date;

  Reservation({
    this.roomId,
    this.guestId,
    this.roomName,
    this.roomNumber,
    this.firstName,
    this.lastName,
    this.date,
  });

  factory Reservation.fromJson(Map<String, dynamic> json) {
    print(json['date'] != null);
    return Reservation(
      roomId: json['roomId'],
      guestId: json['guestId'],
      roomName: json['roomName'],
      roomNumber: json['roomNumber'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      date: json['date'] != null
          ? DateTime.parse(json['date']?.toString() ?? null)
          : null,
    );
  }
}
