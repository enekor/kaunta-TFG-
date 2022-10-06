import 'package:get/get.dart';
import 'package:kaunta/model/modelo.dart';

class Listado {
  static final Listado _listadoInstance = Listado._internal();

  factory Listado() {
    return _listadoInstance;
  }
  Listado._internal();

  RxList<Grupo> grupos = <Grupo>[].obs;
  Grupo gActual = Grupo();
  Contador cActual = Contador();

  RxBool verGrupos = true.obs;
}
