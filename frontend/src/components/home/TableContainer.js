import React from 'react';
import { useNavigate } from 'react-router-dom';
import Table from './Table';
import { AiOutlineEye, AiOutlineEdit, AiOutlineDelete, AiFillStar, AiOutlineStar } from 'react-icons/ai';

const TableContainer = () => {
    const navigate = useNavigate();

    const data = [
        { id: 1, star: true, companyName: 'Company A', role: 'Developer', appliedOn: '2024-03-01', status: 'Pending',field1:'field1', field2:'filed2' },
        { id: 2, star: true, companyName: 'Company B', role: 'Tester', appliedOn: '2024-04-01', status: 'Pending',field1:'field1', field2:'filed2' },
        // Add more data objects as needed
    ];

    const columns = [
        {
            Header: 'Starred',
            accessor: 'star',
            Cell: ({ row }) => (
                <div>
                    {row.original.star ? <AiFillStar className="action-icon"/> : <AiOutlineStar className="action-icon"/>}
                </div>
            )
        },
        { Header: 'Company Name', accessor: 'companyName' },
        { Header: 'Role', accessor: 'role' },
        { Header: 'Applied On', accessor: 'appliedOn' },
        { Header: 'Status', accessor: 'status' },
        {
            Header: 'Actions',
            accessor: 'actions',
            Cell: ({ row }) => (
                <>
                    <AiOutlineEye onClick={() => handleView(row.original)} className="action-icon" />
                    <AiOutlineEdit onClick={() => handleEdit(row.original)} className="action-icon" />
                    <AiOutlineDelete onClick={() => handleDelete(row.original)} className="action-icon" />
                </>
            )
        }
    ];

    // Placeholder functions for handling actions
    const handleView = (rowData) => {
        console.log('View:', rowData);
        navigate('/details', { state: { rowData } });
    };

    const handleEdit = (rowData) => {
        console.log('Edit:', rowData);
        navigate('/edit', { state: { rowData } });
    };

    const handleDelete = (rowData) => {
        console.log('Delete:', rowData);
    };

    return <Table data={data} columns={columns} iconStyle={{ fontSize: '24px', marginRight: '12px' }} />;
};

export default TableContainer;