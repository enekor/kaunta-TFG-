import 'dart:convert';
import 'dart:io';

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
  if (ApiCall().conectado == false) {
    final file = await _localFile();

    json = file.readAsStringSync();
  } else {
    json = await ApiCall().me();
  }

  if (json != "") {
    Listado().usuario.value = User.fromJson(jsonDecode(json));
  }

  Listado().leido.value = true;
}

saveCounters() async {
  final file = await _localFile();

  await file.writeAsString(jsonEncode(jsonEncode(Listado().usuario.value)));
}
