class CrearGrupo {
  int? id;
  String? nombre;
  int? user;

  CrearGrupo({this.id, this.nombre, this.user});

  CrearGrupo.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    user = json['user'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['nombre'] = nombre;
    data['user'] = user;
    return data;
  }
}
