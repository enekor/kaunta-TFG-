import 'dart:convert';
import 'dart:io';

import 'package:kaunta/home/globales.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:path_provider/path_provider.dart';

Future<File> _localFile() async {
  var dir = await getApplicationDocumentsDirectory();
  return File('${dir.path}/contadoresFlutter/counters.json')
      .create(recursive: true);
}

loadCounters() async {
  String json;
  if (Globales().conectado == false) {
    final file = await _localFile();

    json = file.readAsStringSync();

    if (json != "") {
      Listado().usuario.value = User.fromJson(jsonDecode(json));
    }
  } else {
    await ApiCall().me();
  }

  Listado().leido.value = true;
}

saveCounters() async {
  final file = await _localFile();

  User u = Listado().usuario.value;
  String a = jsonEncode(Listado().usuario.value.toJson());

  await file.writeAsString(jsonEncode(Listado().usuario.value.toJson()));
}
