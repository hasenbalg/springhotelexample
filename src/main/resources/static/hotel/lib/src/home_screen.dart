import 'dart:convert';
import 'package:intl/intl.dart';

import 'package:flutter/material.dart';
import 'package:hotel/src/model/reservation.dart';

import 'package:http/http.dart' as http;

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Future<List<Reservation>> reservations;

  bool _showOnlyReserved = false;

  DateTime selectedDate = DateTime(2017, 1, 1);

  Future<void> _selectDate(BuildContext context) async {
    final DateTime picked = await showDatePicker(
        context: context,
        initialDate: selectedDate,
        firstDate: DateTime(2015, 8),
        lastDate: DateTime(2101));
    if (picked != null && picked != selectedDate)
      setState(() {
        selectedDate = picked;
        reservations = fetchReservations();
      });
  }

  @override
  void initState() {
    super.initState();
    reservations = fetchReservations();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(selectedDate.toString()),
        actions: [
          IconButton(
            icon: Icon(_showOnlyReserved
                ? Icons.check_box
                : Icons.check_box_outline_blank),
            onPressed: () {
              setState(() {
                _showOnlyReserved = !_showOnlyReserved;
              });
            },
          )
        ],
      ),
      body: FutureBuilder<List<Reservation>>(
        future: reservations,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            var list = _showOnlyReserved
                ? snapshot.data.where((r) => r.lastName != null).toList()
                : snapshot.data;
            return ListView.builder(
              itemCount: list.length,
              itemBuilder: (BuildContext context, int index) => Card(
                child: ListTile(
                  title: Text(list[index].roomName),
                  leading: Text('${list[index].roomNumber}'),
                ),
              ),
            );
          } else if (snapshot.hasError) {
            return Text("${snapshot.error}");
          }

          return CircularProgressIndicator();
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _selectDate(context),
        child: Icon(Icons.calendar_today),
      ),
    );
  }

  Future<List<Reservation>> fetchReservations() async {
    String formattedDate = DateFormat('yyyy-MM-dd').format(selectedDate);
    print(formattedDate);
    final response = await http.get('/api/reservations/$formattedDate');
    print(response.body);

    if (response.statusCode == 200) {
      Iterable l = json.decode(response.body);
      List<Reservation> reservations =
          l.map((e) => Reservation.fromJson(e)).toList();

      return reservations;
    } else {
      throw Exception('Failed to load reservations');
    }
  }
}
