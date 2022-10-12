import 'package:flutter/material.dart';
import 'package:kaunta/home/botones.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Temas().getPrimary()),
      home: const Scaffold(
        body: Botones(),
      ),
    );
  }

  changeTheme(BuildContext context) async {
    final prefs = await SharedPreferences.getInstance();

    Widget chip(bool isSelected, String texto, int valor) {
      bool seleccionado = isSelected;
      return ChoiceChip(
        label: Text(
          texto,
          style: TextStyle(
              color: seleccionado == true ? Colors.white : Colors.black),
        ),
        selected: seleccionado,
        selectedColor: Temas().getPrimary(),
        onSelected: (value) {
          if (value) {
            Temas().actual.value = valor;
            seleccionado = value;
            prefs.setInt('temaActual', valor);
          }
        },
      );
    }
  }
}


