import React, { useState } from 'react';
import { useTable } from 'react-table';
import { AiOutlineEye, AiOutlineEdit, AiOutlineDelete, AiFillStar, AiOutlineStar } from 'react-icons/ai';
import './table.css';

const Table = ({ data, columns }) => {
    const [starredRows, setStarredRows] = useState([]);

    const handleStarToggle = (rowId) => {
        if (starredRows.includes(rowId)) {
            setStarredRows(starredRows.filter((id) => id !== rowId));
        } else {
            setStarredRows([...starredRows, rowId]);
        }
    };

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = useTable({
        columns,
        data,
    });

    return (
        <div >
            <table {...getTableProps()} className="table">
                <thead>
                {headerGroups.map(headerGroup => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                            <th {...column.getHeaderProps()}>{column.render('Header')}</th>
                        ))}
                    </tr>
                ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                {rows.map(row => {
                    prepareRow(row);
                    const isStarred = starredRows.includes(row.original.id);
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map(cell => {
                                if (cell.column.id === 'star') {
                                    return (
                                        <td {...cell.getCellProps()}>
                                            {isStarred ? (
                                                <AiFillStar
                                                    className="action-icon starred"
                                                    onClick={() => handleStarToggle(row.original.id)}
                                                />
                                            ) : (
                                                <AiOutlineStar
                                                    className="action-icon"
                                                    onClick={() => handleStarToggle(row.original.id)}
                                                />
                                            )}
                                        </td>
                                    );
                                }
                                return <td {...cell.getCellProps()}>{cell.render('Cell')}</td>;
                            })}
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </div>
    );
};

export default Table;