import 'dart:convert';
import 'dart:io';

import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:path_provider/path_provider.dart';

Future<File> _localFile() async {
  var dir = await getApplicationDocumentsDirectory();
  return File('${dir.path}/contadoresFlutter/counters.json')
      .create(recursive: true);
}

loadCounters() async {
  final file = await _localFile();

  String json = file.readAsStringSync();
  if (json != "") {
    Listado().usuario.grupos!.value = User.fromJson(jsonDecode(json)).grupos!;
  }
  Listado().leido.value = true;
}

saveCounters() async {
  final file = await _localFile();

  await file
      .writeAsString(jsonEncode(Listado().usuario.toJson(Listado().usuario)));
}
