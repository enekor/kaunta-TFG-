import 'package:flutter/material.dart';
import 'package:get/get.dart';

class Temas {
  static final Temas _temasInstance = Temas._internal();

  RxInt actual = 1.obs;
  RxBool cImagenValido = true.obs;
  RxBool cContadorValido = true.obs;

  factory Temas() {
    return _temasInstance;
  }
  Temas._internal();

  MaterialColor getPrimary() {
    if (actual.value == 1) {
      return TemaClaro().primario;
    } else {
      return TemaOscuro().primario;
    }
  }

  Color getSecondary() {
    if (actual.value == 1) {
      return TemaClaro().secundario;
    } else {
      return TemaOscuro().secundario;
    }
  }

  Color getBackground() {
    if (actual.value == 1) {
      return TemaClaro().fondo;
    } else {
      return TemaOscuro().fondo;
    }
  }

  Color getTextColor() {
    if (actual.value == 1) {
      return TemaClaro().texto;
    } else {
      return TemaOscuro().texto;
    }
  }

  Color getButtonTextColor() {
    if (actual.value == 1) {
      return TemaClaro().buttonTextColor;
    } else {
      return TemaOscuro().buttonTextColor;
    }
  }
}

class TemaClaro {
  static final TemaClaro _temaClaroInstance = TemaClaro._internal();

  MaterialColor primario = Colors.purple;
  Color secundario = const Color.fromARGB(255, 222, 155, 227);
  Color fondo = Colors.white;
  Color texto = Colors.black54;
  Color buttonTextColor = Colors.white;

  factory TemaClaro() {
    return _temaClaroInstance;
  }

  TemaClaro._internal();
}

class TemaOscuro {
  static final TemaOscuro _temaOscuroInstance = TemaOscuro._internal();

  MaterialColor primario = Colors.blueGrey;
  Color secundario = const Color.fromARGB(27, 38, 42, 238);
  Color fondo = const Color.fromARGB(255, 0, 1, 24);
  Color texto = Colors.white60;
  Color buttonTextColor = Colors.blueGrey.shade300;

  factory TemaOscuro() {
    return _temaOscuroInstance;
  }

  TemaOscuro._internal();
}

Color cambiarColor(int tipo) {
  Color c;

  if (tipo == 1) {
    Temas().cContadorValido.value
        ? c = Temas().getSecondary()
        : c = Colors.redAccent;
  } else if (tipo == -1) {
    c = Temas().getSecondary();
  } else {
    Temas().cImagenValido.value
        ? c = Temas().getSecondary()
        : c = Colors.redAccent;
  }

  return c;
}
