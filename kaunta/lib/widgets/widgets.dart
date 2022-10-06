import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';

Widget cTextField(dynamic onChange, String label, IconData icono) => TextField(
    style: TextStyle(color: Temas().getTextColor()),
    decoration: InputDecoration(
      labelStyle: TextStyle(color: Temas().getTextColor()),
      suffixIcon: Icon(icono, color: Colors.purple),
      labelText: label,
      enabledBorder: OutlineInputBorder(
        borderRadius: BorderRadius.circular(16.0),
        borderSide: BorderSide(color: Temas().getSecondary()),
      ),
      focusedBorder: OutlineInputBorder(
        borderSide: BorderSide(color: Temas().getSecondary()),
      ),
    ),
    onChanged: onChange);

Widget cGroupListItem(Grupo g, int index) => Obx(
      () => Card(
        color: Temas().getSecondary(),
        elevation: 10.0,
        shape: RoundedRectangleBorder(borderRadius: getBorderRadius(index)),
        child: Padding(
          padding: const EdgeInsets.all(25.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Text(g.nombre!.value),
              Text(g.counters!.value.length.toString()),
            ],
          ),
        ),
      ),
    );

BorderRadiusGeometry getBorderRadius(int pos) {
  BorderRadiusGeometry ret = BorderRadius.circular(25);

  if (Listado().grupos.length != 1) {
    if (pos == 0) {
      ret = const BorderRadius.only(
          bottomLeft: Radius.circular(5),
          bottomRight: Radius.circular(5),
          topLeft: Radius.circular(25),
          topRight: Radius.circular(25));
    } else if (pos == Listado().grupos.length - 1) {
      ret = const BorderRadius.only(
          bottomLeft: Radius.circular(25),
          bottomRight: Radius.circular(25),
          topLeft: Radius.circular(5),
          topRight: Radius.circular(5));
    } else {
      ret = BorderRadius.circular(5);
    }
  }

  return ret;
}
