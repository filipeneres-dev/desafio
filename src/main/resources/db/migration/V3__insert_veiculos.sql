-- V3__insert_veiculos.sql

INSERT INTO veiculo (id, placa, preco_anuncio, ano, preco_fipe, data_cadastro, modelo_id) VALUES
                                                                                              (uuid_generate_v4(), 'ABC1D23', 25000.00, 2015, 23000.00, current_timestamp, '5bc16064-d3ee-4aed-a264-a914233d0c4f'),
                                                                                              (uuid_generate_v4(), 'XYZ9E87', 42000.00, 2018, 41000.00, current_timestamp, 'c08f77df-c6e0-4e73-a378-42bb83361266'),
                                                                                              (uuid_generate_v4(), 'KLM3F56', 37000.00, 2020, 39000.00, current_timestamp, '828bd4bf-ea80-4449-bf8f-154cda91d864');
